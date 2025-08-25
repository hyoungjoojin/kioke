import { getCollections } from '@/app/api/collection';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type QueryOptions = UseQueryOptions<Collection[], KiokeError>;

const queryKey = () => {
  return ['collections'] as const;
};

const defaultOptions = (): QueryOptions => ({
  queryKey: queryKey(),
  queryFn: async () => getCollections().then((res) => unwrap(res)),
});

function useCollectionsQuery(customOptions?: Partial<QueryOptions>) {
  return useQuery({
    ...defaultOptions(),
    ...customOptions,
  });
}

export {
  useCollectionsQuery,
  queryKey as collectionsQueryKey,
  defaultOptions as collectionsQueryOptions,
};
