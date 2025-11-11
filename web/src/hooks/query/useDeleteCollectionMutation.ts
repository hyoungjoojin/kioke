import deleteCollection, {
  type DeleteCollectionParams,
} from '@/app/api/collection/deleteCollection';
import { getQueryClient } from '@/lib/query';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type UseDeleteCollectionMutationOptions = UseMutationOptions<
  void,
  KiokeError,
  DeleteCollectionParams,
  void
>;

function useDeleteCollectionMutation(
  options?: UseDeleteCollectionMutationOptions,
) {
  const queryClient = getQueryClient();

  const base: UseDeleteCollectionMutationOptions = {
    mutationFn: async (params) =>
      deleteCollection(params).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['collections'],
      });
    },
  };

  return useMutation({ ...base, ...options });
}

export default useDeleteCollectionMutation;
