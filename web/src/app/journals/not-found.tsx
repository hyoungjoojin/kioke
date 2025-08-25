import BaseHeader from '@/components/header/BaseHeader';
import UnauthenticatedHeader from '@/components/header/UnauthenticatedHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { ErrorCode } from '@/constant/error';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

export default async function JournalNotFound() {
  let isAuthenticated = true;

  const queryClient = getQueryClient();
  await queryClient.fetchQuery(myProfileQueryOptions()).catch((error) =>
    handleError(error, {
      [ErrorCode.ACCESS_DENIED]: () => {
        isAuthenticated = false;
      },
    }),
  );

  return (
    <BaseLayout
      header={
        isAuthenticated ? (
          <HydrationBoundary state={dehydrate(queryClient)}>
            <BaseHeader selectedTab='none' />
          </HydrationBoundary>
        ) : (
          <UnauthenticatedHeader />
        )
      }
      main={
        <div className='h-full flex flex-col items-center justify-center'>
          <span className='text-xl'>404</span>
          <span className='text-3xl'>Journal Not Found</span>
        </div>
      }
    />
  );
}
