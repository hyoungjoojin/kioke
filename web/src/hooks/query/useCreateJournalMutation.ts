import createJournal, {
  type CreateJournalParams,
} from '@/app/api/journal/createJournal';
import { getQueryClient } from '@/lib/query';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type UseCreateJournalMutationOptions = UseMutationOptions<
  Journal,
  KiokeError,
  CreateJournalParams,
  void
>;

function useCreateJournalMutation(options?: UseCreateJournalMutationOptions) {
  const queryClient = getQueryClient();

  const base: UseCreateJournalMutationOptions = {
    mutationFn: async (params) =>
      createJournal(params).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['journals'],
      });
    },
  };

  return useMutation({
    ...base,
    ...options,
  });
}

export default useCreateJournalMutation;
