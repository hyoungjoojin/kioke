import { EditableDiv } from '@/components/ui/editable';
import { Skeleton } from '@/components/ui/skeleton';
import { Routes } from '@/constant/routes';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import { usePageQuery, useUpdatePageMutation } from '@/query/page';
import Link from 'next/link';

interface PageTitleProps {
  pageId: string;
}

export default function PageTitle({ pageId }: PageTitleProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { data: journal } = useGetJournalByIdQuery(
    {
      path: { journalId: page?.journalId ?? '' },
    },
    {
      enabled: page !== undefined,
    },
  );

  const { mutate: updatePage } = useUpdatePageMutation({
    id: pageId,
  });

  return (
    <>
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

      {page ? (
        <EditableDiv
          initialContent={page.title}
          onSubmit={(title) => {
            updatePage({
              title,
            });
          }}
          className='text-4xl mt-2 mb-5'
        />
      ) : (
        <Skeleton />
      )}
    </>
  );
}
