import { journalsQueryKey } from './getJournals';
import type { CreateJournalRequest } from '@/app/api/journal';
import { createJournal } from '@/app/api/journal';
import { getQueryClient } from '@/lib/query';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = Journal;
type TError = KiokeError;
type TVariables = CreateJournalRequest;
type TContext = void;

function useCreateJournalMutation() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      createJournal(requestBody).then((result) => unwrap(result)),
    onSuccess: async (_data, _variables) => {
      // TODO: Instead of invalidating the journals query, add the newly created journal to the
      // query result.
      await queryClient.invalidateQueries({
        queryKey: journalsQueryKey(),
      });
    },
  });
}

export { useCreateJournalMutation };
