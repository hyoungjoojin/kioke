import { getPage } from '@/app/api/page';
import type { KiokeError } from '@/constant/error';
import type { Page } from '@/types/page';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Page;
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
    queryFn: async () => getPage(params).then((response) => unwrap(response)),
  };
}

function usePageQuery(keyParams: QueryParams) {
  return useQuery(options(keyParams));
}

export { usePageQuery, key as pageQueryKey, options as pageQueryOptions };
