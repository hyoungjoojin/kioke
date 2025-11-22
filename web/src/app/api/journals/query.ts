import {
  type GetJournalsParams,
  type GetJournalsResponse,
  getJournals,
} from '.';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type InfiniteData, useInfiniteQuery } from '@tanstack/react-query';

function useGetJournalsQuery({ query }: { query?: string }) {
  return useInfiniteQuery<
    GetJournalsResponse,
    KiokeError,
    InfiniteData<GetJournalsResponse>,
    string[],
    GetJournalsParams | undefined
  >({
    queryKey: ['journals', query || ''],
    queryFn: async ({ pageParam }) =>
      getJournals({
        params: {
          ...pageParam,
          query,
        },
      }).then((response) => unwrap(response)),
    initialPageParam: {
      size: 10,
    },
    getNextPageParam: (lastPage) => {
      if (!lastPage.hasNext) {
        return undefined;
      }

      return {
        size: lastPage.journals.length,
        cursor: lastPage.cursor,
      };
    },
  });
}

export { useGetJournalsQuery };
