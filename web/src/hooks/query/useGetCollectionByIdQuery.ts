import getCollectionById, {
  type GetCollectionByIdParams,
} from '@/app/api/collection/getCollectionById';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type UseGetCollectionByIdQueryOptions = UseQueryOptions<Collection, KiokeError>;

function useGetCollectionByIdQuery(
  params: GetCollectionByIdParams,
  options?: UseGetCollectionByIdQueryOptions,
) {
  const base: UseGetCollectionByIdQueryOptions = {
    queryKey: ['collection', params],
    queryFn: async () => getCollectionById(params).then((res) => unwrap(res)),
  };

  return useQuery({ ...base, ...options });
}

export default useGetCollectionByIdQuery;
