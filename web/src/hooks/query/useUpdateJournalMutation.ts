import updateJournal, {
  type UpdateJournalParams,
} from '@/app/api/journal/updateJournal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type UseUpdateJournalMutationOptions = UseMutationOptions<
  void,
  KiokeError,
  UpdateJournalParams,
  void
>;

function useUpdateJournalMutation(options?: UseUpdateJournalMutationOptions) {
  const base: UseUpdateJournalMutationOptions = {
    mutationFn: async (params) =>
      updateJournal(params).then((result) => unwrap(result)),
  };

  return useMutation({ ...base, ...options });
}

export default useUpdateJournalMutation;
