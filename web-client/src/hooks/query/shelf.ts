import { getShelves, updateShelf } from "@/app/api/shelf";
import { getQueryClient } from "@/components/providers/QueryProvider";
import { Shelf } from "@/types/primitives/shelf";
import { useMutation, useQuery } from "@tanstack/react-query";

export const useShelvesQuery = () => {
  return useQuery({
    queryKey: ["shelves"],
    queryFn: getShelves,
    staleTime: 60 * 1000,
  });
};

export const useUpdateShelfMutation = (targetShelf: Shelf) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (data: { name: string }) => updateShelf(targetShelf, data.name),
    onMutate: async (variables) => {
      await queryClient.cancelQueries({
        queryKey: ["shelves"],
      });

      queryClient.setQueryData(["shelves"], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.id !== targetShelf.id
            ? shelf
            : { ...shelf, name: variables.name };
        });
      });
    },
    onSuccess: () => {},
    onError: () => {
      queryClient.setQueryData(["shelves"], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.id !== targetShelf.id
            ? shelf
            : { ...shelf, name: targetShelf.name };
        });
      });
    },
  });
};
