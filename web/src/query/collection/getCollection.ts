import { getCollection } from '@/app/api/collection';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Collection;
type TError = KiokeError;

type QueryParams = {
  id: string;
};

type QueryOptions = UseQueryOptions<TData, TError>;

function key({ id }: QueryParams) {
  return ['collections', id] as const;
}

const options = (params: QueryParams): QueryOptions => ({
  queryKey: key(params),
  queryFn: async () => getCollection(params).then((res) => unwrap(res)),
});

function useCollectionQuery(
  params: QueryParams,
  custom?: Partial<QueryOptions>,
) {
  return useQuery({
    ...options(params),
    ...custom,
  });
}

export {
  useCollectionQuery,
  key as getCollectionQueryKey,
  options as getCollectionDefaultQueryOptions,
};
