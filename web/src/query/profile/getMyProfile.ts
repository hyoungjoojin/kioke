import { getMyProfile } from '@/app/api/profile';
import type { KiokeError } from '@/constant/error';
import type { MyProfile } from '@/types/profile';
import { unwrap } from '@/util/result';
import type { UseQueryOptions } from '@tanstack/react-query';
import { useQuery } from '@tanstack/react-query';

type TData = MyProfile;
type TError = KiokeError;

type QueryOptions = UseQueryOptions<TData, TError>;

function key() {
  return ['users', 'me'];
}

function options(): QueryOptions {
  return {
    queryKey: key(),
    queryFn: async () => getMyProfile().then((data) => unwrap(data)),
  };
}

function useMyProfileQuery(custom?: Partial<QueryOptions>) {
  return useQuery({
    ...options(),
    ...custom,
  });
}

export {
  useMyProfileQuery,
  key as myProfileQueryKey,
  options as myProfileQueryOptions,
};
