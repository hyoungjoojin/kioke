import { searchProfiles } from '@/app/api/profile';
import type { Profile } from '@/types/profile';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = Profile[];
type TError = KiokeError;

type QueryOptions = UseQueryOptions<TData, TError>;

function key(query: string) {
  return ['profiles', query];
}

function options(query: string): QueryOptions {
  return {
    queryKey: key(query),
    queryFn: async () => searchProfiles({ query }).then((data) => unwrap(data)),
    enabled: Boolean(query),
  };
}

function useSearchProfilesQuery(query: string, custom?: Partial<QueryOptions>) {
  return useQuery({
    ...options(query),
    ...custom,
  });
}

export {
  useSearchProfilesQuery,
  key as searchProfileQueryKey,
  options as searchProfileQueryOptions,
};
