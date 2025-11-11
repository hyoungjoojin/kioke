import getCollections from '@/app/api/collection/getCollections';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type UseGetCollectionsQueryOptions = UseQueryOptions<Collection[], KiokeError>;

const useGetCollectionsQueryDefaultOptions: UseGetCollectionsQueryOptions = {
  queryKey: ['collection'],
  queryFn: async () => getCollections().then((res) => unwrap(res)),
};

function useGetCollectionsQuery(options?: UseGetCollectionsQueryOptions) {
  return useQuery({ ...useGetCollectionsQueryDefaultOptions, ...options });
}

export default useGetCollectionsQuery;
export { useGetCollectionsQueryDefaultOptions };
