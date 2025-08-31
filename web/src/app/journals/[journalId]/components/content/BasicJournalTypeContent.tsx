import {
  Card,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Routes } from '@/constant/routes';
import { useJournalQuery } from '@/query/journal';
import Link from 'next/link';

interface BasicJournalTypeContentProps {
  journalId: string;
}

export function BasicJournalTypeContent({
  journalId,
}: BasicJournalTypeContentProps) {
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
                <CardDescription>{page.date.toDateString()}</CardDescription>
              </CardHeader>
            </Card>
          </Link>
        );
      })}
    </>
  );
}
