import { getJournals } from '@/app/api/journal';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Journal[];
type TError = KiokeError;

type Options = UseQueryOptions<TData, TError>;

function key() {
  return ['journals'] as const;
}

function options(): Options {
  return {
    queryKey: key(),
    queryFn: async () => getJournals().then((response) => unwrap(response)),
  };
}

function useJournalsQuery(custom?: Partial<Options>) {
  return useQuery({
    ...options(),
    ...custom,
  });
}

export {
  useJournalsQuery,
  key as journalsQueryKey,
  options as journalsQueryOptions,
};
