import { myDashboardQueryKey } from './getMyDashboard';
import type { UpdateDashboardRequest } from '@/app/api/dashboard';
import { updateDashboard } from '@/app/api/dashboard';
import { getQueryClient } from '@/lib/query';
import { useDashboardActions } from '@/store/dashboard';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdateDashboardRequest;
type TContext = void;

type QueryOptions = UseMutationOptions<TData, TError, TVariables, TContext>;

function useUpdateDashboardMutation(custom?: Partial<QueryOptions>) {
  const queryClient = getQueryClient();

  const { setIsEditingDraft } = useDashboardActions();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updateDashboard(requestBody).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: myDashboardQueryKey(),
      });

      setIsEditingDraft(false);
    },
    ...custom,
  });
}

export { useUpdateDashboardMutation };
