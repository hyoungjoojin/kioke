import { getPageImages } from '@/app/api/page';
import type { ImageBlock } from '@/types/page';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = ImageBlock[];
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
      getPageImages(params).then((response) => unwrap(response)),
  };
}

function usePageImagesQuery(keyParams: QueryParams) {
  return useQuery(options(keyParams));
}

export {
  usePageImagesQuery,
  key as pageImagesQueryKey,
  options as pageImagesQueryOptions,
};
