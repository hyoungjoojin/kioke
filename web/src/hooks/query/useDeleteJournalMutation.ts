import deleteJournal, {
  type DeleteJournalParams,
} from '@/app/api/journal/deleteJournal';
import { getQueryClient } from '@/lib/query';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type UseDeleteJournalMutationOptions = UseMutationOptions<
  void,
  KiokeError,
  DeleteJournalParams,
  void
>;

function useDeleteJournalMutation(options?: UseDeleteJournalMutationOptions) {
  const queryClient = getQueryClient();

  const base: UseDeleteJournalMutationOptions = {
    mutationFn: async (params) =>
      deleteJournal(params).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['journals'],
      });
    },
  };

  return useMutation({ ...base, ...options });
}

export default useDeleteJournalMutation;
