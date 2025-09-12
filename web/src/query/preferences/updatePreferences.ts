import {
  type UpdatePreferencesRequest,
  updatePreferences,
} from '@/app/api/preferences';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryKey } from '@/query/profile';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdatePreferencesRequest;
type TContext = void;

function useUpdatePreferencesMutation() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updatePreferences(requestBody).then((response) => unwrap(response)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: myProfileQueryKey(),
      });
    },
  });
}

export { useUpdatePreferencesMutation };
