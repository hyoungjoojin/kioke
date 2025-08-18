import { myProfileQueryKey } from './getMyProfile';
import type { UpdateMyProfileRequestBody } from '@/app/api/profile';
import { updateMyProfile } from '@/app/api/profile';
import type { KiokeError } from '@/constant/error';
import { getQueryClient } from '@/lib/query';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdateMyProfileRequestBody;
type TContext = void;

function useUpdateMyProfileMutationQuery() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updateMyProfile(requestBody).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: myProfileQueryKey(),
      });
    },
  });
}

export { useUpdateMyProfileMutationQuery };
