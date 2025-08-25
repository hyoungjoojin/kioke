import { getJournal } from '@/app/api/journal';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Journal;
type TError = KiokeError;

type Params = {
  journalId: string;
};

type Options = UseQueryOptions<TData, TError>;

function key({ journalId }: Params) {
  return ['journals', journalId] as const;
}

function options(params: Params): Options {
  return {
    queryKey: key(params),
    queryFn: async () =>
      getJournal(params).then((response) => unwrap(response)),
  };
}

function useJournalQuery(params: Params, custom?: Partial<Options>) {
  return useQuery({
    ...options(params),
    ...custom,
  });
}

export {
  useJournalQuery,
  key as journalQueryKey,
  options as journalQueryOptions,
};
