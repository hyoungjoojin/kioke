import type { CreateJournalRequest } from '@/app/api/journal';
import { createJournal } from '@/app/api/journal';
import { getQueryClient } from '@/lib/query';
import { collectionsQueryKey, getCollectionQueryKey } from '@/query/collection';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = Journal;
type TError = KiokeError;
type TVariables = CreateJournalRequest;
type TContext = void;

type QueryParams = {
  collectionId: string;
};

function useCreateJournalMutation(params: QueryParams) {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      createJournal(requestBody).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: getCollectionQueryKey({
          id: params.collectionId,
        }),
      });

      await queryClient.invalidateQueries({
        queryKey: collectionsQueryKey(),
      });
    },
  });
}

export { useCreateJournalMutation };
