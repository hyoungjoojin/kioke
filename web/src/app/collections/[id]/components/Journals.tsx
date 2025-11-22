'use client';

import { Button } from '@/components/ui/button';
import { Routes } from '@/constant/routes';
import useCreateJournalMutation from '@/hooks/query/useCreateJournalMutation';
import useGetCollectionByIdQuery from '@/hooks/query/useGetCollectionByIdQuery';
import { getQueryClient } from '@/lib/query';
import Link from 'next/link';

interface JournalsProps {
  collectionId: string;
}

function Journals({ collectionId }: JournalsProps) {
  const { data: collection } = useGetCollectionByIdQuery({
    path: { collectionId },
  });

  const { mutate: createJournal } = useCreateJournalMutation();

  return (
    <div className='flex flex-col gap-4 mt-4'>
      <Button
        variant='icon'
        icon='plus'
        className='self-end'
        onClick={() => {
          createJournal(
            {
              body: {
                collectionId,
                title: 'New Journal',
              },
            },
            {
              onSuccess: () => {
                const queryClient = getQueryClient();
                queryClient.invalidateQueries({
                  queryKey: [
                    'collection',
                    {
                      path: { collectionId },
                    },
                  ],
                });
              },
            },
          );
        }}
      />

      <div>
        {collection &&
          collection.journals.map((journal) => (
            <div key={journal.id} className='m-2 hover:underline'>
              <Link href={Routes.JOURNAL(journal.id)}>{journal.title}</Link>
            </div>
          ))}
      </div>
    </div>
  );
}

export default Journals;
