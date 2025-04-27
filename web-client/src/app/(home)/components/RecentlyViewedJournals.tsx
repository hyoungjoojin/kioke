'use client';

import { Card, CardTitle } from '@/components/ui/card';
import { useGetJournals } from '@/hooks/query/journal';
import { Clock } from 'lucide-react';
import { useRouter } from 'next/navigation';

export default function RecentlyViewedJournals() {
  const router = useRouter();
  const { data: getJournalsResponse } = useGetJournals();

  return (
    <>
      <h1 className='flex items-center mb-2'>
        <Clock size={16} className='mr-1' />
        <span>Recently Viewed</span>
      </h1>

      <div className='flex'>
        {getJournalsResponse?.journals &&
          getJournalsResponse.journals.map((journal, index) => (
            <Card
              key={index}
              className='h-36 w-36 mx-1 hover:cursor-pointer'
              onClick={() => {
                router.push(`/journal/${journal.journalId}/preview`);
              }}
            >
              <CardTitle className='m-2'>{journal.title}</CardTitle>
            </Card>
          ))}
      </div>
    </>
  );
}
