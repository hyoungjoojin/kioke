import { journalQueryKey } from './getJournal';
import type { UpdateJournalRequest } from '@/app/api/journal';
import { updateJournal } from '@/app/api/journal';
import { getQueryClient } from '@/lib/query';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdateJournalRequest;
type TContext = void;

type QueryParams = {
  id: string;
};

function useUpdateJournalMutation(params: QueryParams) {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updateJournal(params, requestBody).then((response) => unwrap(response)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: journalQueryKey({
          journalId: params.id,
        }),
      });
    },
  });
}

export { useUpdateJournalMutation };
