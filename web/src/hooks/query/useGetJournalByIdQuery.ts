import getJournalById, {
  type GetJournalByIdParams,
} from '@/app/api/journal/getJournalById';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type UseGetJournalByIdQueryOptions = UseQueryOptions<Journal, KiokeError>;

function useGetJournalByIdQuery(
  params: GetJournalByIdParams,
  options?: UseGetJournalByIdQueryOptions,
) {
  const base: UseGetJournalByIdQueryOptions = {
    queryKey: ['journal', params],
    queryFn: async () => getJournalById(params).then((res) => unwrap(res)),
  };

  return useQuery({
    ...base,
    ...options,
  });
}

export default useGetJournalByIdQuery;
