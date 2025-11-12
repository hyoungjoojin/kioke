import getJournalById, {
  type GetJournalByIdParams,
} from '@/app/api/journal/getJournalById';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { type UseQueryOptions, useQuery } from '@tanstack/react-query';

type UseGetJournalByIdQueryOptions = UseQueryOptions<Journal, KiokeError>;

const UseGetJournalByIdQueryDefaultOptions = (
  params: GetJournalByIdParams,
) => ({
  queryKey: ['journal', params],
  queryFn: async () => getJournalById(params).then((res) => unwrap(res)),
});

function useGetJournalByIdQuery(
  params: GetJournalByIdParams,
  options?: Partial<UseGetJournalByIdQueryOptions>,
) {
  return useQuery({
    ...UseGetJournalByIdQueryDefaultOptions(params),
    ...options,
  });
}

export default useGetJournalByIdQuery;
export { UseGetJournalByIdQueryDefaultOptions };
