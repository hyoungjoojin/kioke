'use client';

import { useTransaction } from '@/components/provider/TransactionProvider';
import { Skeleton } from '@/components/ui/skeleton';
import { Spinner } from '@/components/ui/spinner';
import { Routes } from '@/constant/routes';
import { useJournalQuery } from '@/query/journal';
import { usePageQuery } from '@/query/page';
import Link from 'next/link';
import { useParams } from 'next/navigation';

export default function PageTitle() {
  const { pageId } = useParams<{ pageId: string }>();

  const { data: page, isSuccess: isPageReady } = usePageQuery({ id: pageId });
  const { data: journal } = useJournalQuery(
    { journalId: page?.journalId || '' },
    {
      enabled: isPageReady,
    },
  );
  const { status } = useTransaction();

  return (
    <>
      <div className='h-10 flex items-center gap-3'>
        {journal ? (
          <Link
            className='hover:underline hover:cursor-pointer'
            href={Routes.JOURNAL(journal.id)}
          >
            {journal && journal.title}
          </Link>
        ) : (
          <Skeleton className='h-full w-36' />
        )}

        {status === 'saving' && <Spinner />}
      </div>
      <div className='text-4xl mt-2 mb-5'>
        {(page && page.title) || 'Untitled'}
      </div>
    </>
  );
}
