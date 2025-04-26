'use client';

import { Card, CardContent, CardTitle } from '@/components/ui/card';
import { useJournalsQuery } from '@/hooks/query/journal';
import { Clock } from 'lucide-react';
import { useRouter } from 'next/navigation';

export default function RecentlyViewedJournals() {
  const router = useRouter();
  const { data: journals } = useJournalsQuery();

  return (
    <>
      <h1 className='flex items-center mb-2'>
        <Clock size={16} className='mr-1' />
        <span>Recently Viewed</span>
      </h1>

      <div className='flex'>
        {journals &&
          journals.map((journal, index) => (
            <Card
              key={index}
              className='h-36 w-36 mx-1 hover:cursor-pointer'
              onClick={() => {
                router.push(`/journal/${journal.journalId}/preview`);
              }}
            >
              <CardTitle className='m-2'>{journal.title}</CardTitle>
              <CardContent>{journal.description}</CardContent>
            </Card>
          ))}
      </div>
    </>
  );
}
