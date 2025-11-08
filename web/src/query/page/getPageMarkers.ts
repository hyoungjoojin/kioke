import { getPageMarkers } from '@/app/api/page';
import type { MarkerBlock } from '@/types/page';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = MarkerBlock[];
type TError = KiokeError;

type QueryParams = {
  id: string;
};

type Options = UseQueryOptions<TData, TError>;

function key({ id }: QueryParams) {
  return ['pages', id] as const;
}

function options(params: QueryParams): Options {
  return {
    queryKey: key(params),
    queryFn: async () =>
      getPageMarkers(params).then((response) => unwrap(response)),
  };
}

function usePageMarkersQuery(keyParams: QueryParams) {
  return useQuery(options(keyParams));
}

export {
  usePageMarkersQuery,
  key as pageMarkersQueryKey,
  options as pageMarkersQueryOptions,
};
