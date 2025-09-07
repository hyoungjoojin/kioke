import { myProfileQueryKey } from './getMyProfile';
import type { UpdateProfileRequest } from '@/app/api/profile';
import { updateProfile } from '@/app/api/profile';
import { getQueryClient } from '@/lib/query';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdateProfileRequest;
type TContext = void;

function useUpdateMyProfileMutationQuery() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updateProfile(requestBody).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: myProfileQueryKey(),
      });
    },
  });
}

export { useUpdateMyProfileMutationQuery };
