import { getMyProfileQueryOptions } from '@/app/api/profiles/query';
import BaseLayout from '@/components/layout/BaseLayout';
import { ErrorCode } from '@/constant/error';
import { getQueryClient } from '@/lib/query';
import { handleError } from '@/util/error';

export default async function JournalNotFound() {
  const queryClient = getQueryClient();
  await queryClient.fetchQuery(getMyProfileQueryOptions).catch((error) =>
    handleError(error, {
      [ErrorCode.ACCESS_DENIED]: () => {},
    }),
  );

  return (
    <BaseLayout>
      <div className='h-full flex flex-col items-center justify-center'>
        <span className='text-xl'>404</span>
        <span className='text-3xl'>Journal Not Found</span>
      </div>
    </BaseLayout>
  );
}
