import { getJournals } from '@/app/api/journal';
import type { Journal } from '@/types/journal';
import type { PagedModel } from '@/types/server';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import {
  type InfiniteData,
  type QueryKey,
  useInfiniteQuery,
} from '@tanstack/react-query';

type TData = {
  content: Journal[];
  page: PagedModel;
};
type TError = KiokeError;
type TPageParam = number;

function key(size: number) {
  return ['journals', size] as const;
}

function useInfiniteJournalsQuery({ size }: { size: number } = { size: 20 }) {
  return useInfiniteQuery<
    TData,
    TError,
    InfiniteData<TData>,
    QueryKey,
    TPageParam
  >({
    queryKey: key(size),
    queryFn: async ({ pageParam }) =>
      getJournals({ size, page: pageParam }).then((response) =>
        unwrap(response),
      ),
    initialPageParam: 0,
    getNextPageParam: (lastPage) =>
      lastPage.page.number < lastPage.page.totalPages
        ? lastPage.page.number + 1
        : null,
  });
}

export { useInfiniteJournalsQuery, key as journalsQueryKey };
