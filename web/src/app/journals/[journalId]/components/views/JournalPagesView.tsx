'use client';

import {
  Card,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Routes } from '@/constant/routes';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import Link from 'next/link';

interface JournalPagesViewParams {
  journalId: string;
}

function JournalPagesView({ journalId }: JournalPagesViewParams) {
  const { data: journal, isPending } = useGetJournalByIdQuery({
    path: { journalId },
  });

  if (isPending) {
    return null;
  }

  if (!journal) {
    return null;
  }

  return (
    <>
      {journal.pages.map((page, index) => {
        return (
          <Link href={Routes.PAGE(page.id)} key={index}>
            <Card className='my-2'>
              <CardHeader>
                <CardTitle>{page.title}</CardTitle>
                <CardDescription>{page.date.toDateString()}</CardDescription>
              </CardHeader>
            </Card>
          </Link>
        );
      })}
    </>
  );
}

export default JournalPagesView;
