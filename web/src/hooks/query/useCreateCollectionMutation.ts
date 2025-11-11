import type { CreateCollectionParams } from '@/app/api/collection/createCollection';
import createCollection from '@/app/api/collection/createCollection';
import { getQueryClient } from '@/lib/query';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseMutationOptions, useMutation } from '@tanstack/react-query';

type UseCreateCollectionMutationOptions = UseMutationOptions<
  Collection,
  KiokeError,
  CreateCollectionParams,
  void
>;

function useCreateCollectionMutation(
  options?: UseCreateCollectionMutationOptions,
) {
  const queryClient = getQueryClient();

  const base: UseCreateCollectionMutationOptions = {
    mutationFn: async (params) =>
      createCollection(params).then((result) => unwrap(result)),
    onSuccess: async () => {
      await queryClient.invalidateQueries({
        queryKey: ['collections'],
      });
    },
  };

  return useMutation({ ...base, ...options });
}

export default useCreateCollectionMutation;
