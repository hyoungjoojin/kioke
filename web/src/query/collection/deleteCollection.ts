import { collectionsQueryKey } from './getCollections';
import { deleteCollection } from '@/app/api/collection';
import { getQueryClient } from '@/lib/query';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = void;
type TContext = void;

function useDeleteCollectionMutationQuery(collectionId: string) {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async () =>
      deleteCollection({
        id: collectionId,
      }).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: collectionsQueryKey(),
      });
    },
  });
}

export { useDeleteCollectionMutationQuery };
