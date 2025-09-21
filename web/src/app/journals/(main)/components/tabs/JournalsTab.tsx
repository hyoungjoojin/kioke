'use client';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { JournalType } from '@/constant/journal';
import { useCreateJournalMutation, useJournalsQuery } from '@/query/journal';

export default function JournalsTab() {
  const { mutate: createJournal } = useCreateJournalMutation();
  const { data: journals } = useJournalsQuery();

  return (
    <>
      <div className='flex gap-2'>
        <div className='grow'>
          <Input type='search' />
        </div>

        <div>
          <Button
            onClick={() => {
              createJournal({
                title: 'Untitled',
                type: JournalType.BASIC,
              });
            }}
          >
            Add Journal
          </Button>
        </div>
      </div>
      <div>
        {journals &&
          journals.map((journal, index) => {
            return <div key={index}>{journal.title}</div>;
          })}
      </div>
    </>
  );
}
