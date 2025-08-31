import { Card, CardHeader, CardTitle } from '@/components/ui/card';
import { Routes } from '@/constant/routes';
import { useJournalQuery } from '@/query/journal';
import Link from 'next/link';

interface ShortJournalContentProps {
  journalId: string;
}

export function ShortJournalContent({ journalId }: ShortJournalContentProps) {
  const { data: journal, isPending } = useJournalQuery({ journalId });

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
              </CardHeader>
            </Card>
          </Link>
        );
      })}
    </>
  );
}
