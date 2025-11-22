import getCollectionById, {
  type GetCollectionByIdParams,
} from '@/app/api/collection/getCollectionById';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type UseGetCollectionByIdQueryOptions = UseQueryOptions<Collection, KiokeError>;

const UseGetCollectionByIdQueryDefaultOptions = (
  params: GetCollectionByIdParams,
) => {
  return {
    queryKey: ['collection', params],
    queryFn: async () => getCollectionById(params).then((res) => unwrap(res)),
  };
};

function useGetCollectionByIdQuery(
  params: GetCollectionByIdParams,
  options?: UseGetCollectionByIdQueryOptions,
) {
  return useQuery({
    ...UseGetCollectionByIdQueryDefaultOptions(params),
    ...options,
  });
}

export default useGetCollectionByIdQuery;
export { UseGetCollectionByIdQueryDefaultOptions };
