import { getShelf, getShelves, updateShelf } from '@/app/api/shelf';
import { getQueryClient } from '@/components/providers/QueryProvider';
import { Shelf } from '@/types/primitives/shelf';
import { useMutation, useQuery } from '@tanstack/react-query';

export const useShelvesQuery = () => {
  return useQuery({
    queryKey: ['shelves'],
    queryFn: getShelves,
    staleTime: 60 * 1000,
  });
};

export const useShelfQuery = (shelfId: string) => {
  return useQuery({
    queryKey: ['shelves', shelfId],
    queryFn: () => getShelf(shelfId),
    retry: false,
    staleTime: 60 * 1000,
  });
};

export const useUpdateShelfMutation = (shelfId: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (data: { name: string }) => updateShelf(shelfId, data.name),
    onMutate: async (variables) => {
      await queryClient.cancelQueries({
        queryKey: ['shelves'],
      });

      queryClient.setQueryData(['shelves'], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.shelfId !== shelfId
            ? shelf
            : { ...shelf, name: variables.name };
        });
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ['shelves', shelfId],
      });
    },
    onError: (_, variables) => {
      queryClient.setQueryData(['shelves'], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.shelfId !== shelfId
            ? shelf
            : { ...shelf, name: variables.name };
        });
      });
    },
  });
};
