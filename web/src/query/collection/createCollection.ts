import { collectionsQueryKey } from './getCollections';
import {
  type CreateCollectionRequest,
  createCollection,
} from '@/app/api/collection';
import { getQueryClient } from '@/lib/query';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = Collection;
type TError = KiokeError;
type TVariables = CreateCollectionRequest;
type TContext = void;

function useCreateCollectionMutationQuery() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      createCollection(requestBody).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: collectionsQueryKey(),
      });
    },
  });
}

export { useCreateCollectionMutationQuery };
