import { getImagesInPage } from '@/app/api/image/getImagesInPage';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type QueryOptions = UseQueryOptions<string[], KiokeError>;

const queryKey = (pageId: string) => {
  return ['images', pageId] as const;
};

const defaultOptions = (pageId: string): QueryOptions => ({
  queryKey: queryKey(pageId),
  queryFn: async () => getImagesInPage(pageId).then((res) => unwrap(res)),
});

function usePageImagesQuery(
  pageId: string,
  customOptions?: Partial<QueryOptions>,
) {
  return useQuery({
    ...defaultOptions(pageId),
    ...customOptions,
  });
}

export {
  usePageImagesQuery,
  queryKey as pageImagesQueryKey,
  defaultOptions as pageImagesQueryOptions,
};
