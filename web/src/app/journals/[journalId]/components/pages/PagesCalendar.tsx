'use client';

import { Calendar } from '@/components/ui/calendar';
import { Card, CardTitle } from '@/components/ui/card';
import { useState } from 'react';

export default function PagesCalendar({ journalId }: { journalId: string }) {
  // const { data } = usePagesQuery(journalId);
  const [date, setDate] = useState(new Date());

  return (
    <div className='w-full flex justify-center'>
      <Calendar
        mode='multiple'
        selected={data?.map((page) => page.date)}
        onSelect={(date) => {
          if (!date || date.length < 1) {
            return;
          }

          setDate(new Date(date[date.length - 1]));
        }}
        className='w-4/5 md:w-3/5 lg:w-[40rem]'
      />

      {false && (
        <Card>
          <CardTitle>{date.toDateString()}</CardTitle>
        </Card>
      )}
    </div>
  );
}
