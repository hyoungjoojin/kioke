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

export const useUpdateShelfMutation = () => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (data: { targetShelf: Shelf; name: string }) =>
      updateShelf(data.targetShelf, data.name),
    onMutate: async (variables) => {
      await queryClient.cancelQueries({
        queryKey: ["shelves"],
      });

      queryClient.setQueryData(["shelves"], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.shelfId !== variables.targetShelf.shelfId
            ? shelf
            : { ...shelf, name: variables.name };
        });
      });
    },
    onSuccess: () => {},
    onError: (_, variables) => {
      queryClient.setQueryData(["shelves"], (old: Shelf[]) => {
        return old.map((shelf) => {
          return shelf.shelfId !== variables.targetShelf.shelfId
            ? shelf
            : { ...shelf, name: variables.targetShelf.name };
        });
      });
    },
  });
};
