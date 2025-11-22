'use client';

import ShareJournalButton from './ShareJournalButton';
import UpdateCover from './actions/UpdateCover';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { EditableDiv } from '@/components/ui/editable';
import { Separator } from '@/components/ui/separator';
import { Skeleton } from '@/components/ui/skeleton';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import useUpdateJournalMutation from '@/hooks/query/useUpdateJournalMutation';
import { cn } from '@/lib/utils';
import { useTranslations } from 'next-intl';
import Image from 'next/image';

export default function JournalTitle({ journalId }: { journalId: string }) {
  const t = useTranslations();

  const { data: journal } = useGetJournalByIdQuery({
    path: {
      journalId,
    },
  });
  const { mutate: updateJournal } = useUpdateJournalMutation();

  if (!journal) {
    return null;
  }

  return (
    <div className=''>
      <div
        className={cn(
          'relative w-full rounded-lg overflow-hidden mb-2 flex items-end',
          journal.cover && 'h-48 md:h-64 shadow-md',
        )}
      >
        {journal.cover && (
          <>
            <Image
              src={journal.cover}
              alt=''
              fill
              className='object-cover z-0'
            />
            <div className='absolute inset-0 bg-black/70 z-10' />
          </>
        )}

        <div className='relative z-20 w-full flex flex-col justify-end gap-y-2 px-6 py-4 h-full'>
          <div>
            <div className='w-full flex justify-between items-end'>
              <div>
                {!journal ? (
                  <Skeleton className='h-full' />
                ) : (
                  <EditableDiv
                    onSubmit={(title) => {
                      updateJournal({
                        path: { journalId },
                        body: { title },
                      });
                    }}
                    initialContent={journal.title}
                    defaultContentOnEmpty={t('journal.main.title.empty')}
                    className={cn(
                      'text-4xl drop-shadow-lg',
                      journal.cover && 'text-white',
                    )}
                  />
                )}
              </div>

              <div className='flex items-center gap-2'>
                <ShareJournalButton
                  journalId={journalId}
                  className={cn(journal.cover && 'text-white')}
                />

                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button
                      variant='icon'
                      icon='ellipsis'
                      className={cn(journal.cover && 'text-white')}
                    />
                  </DropdownMenuTrigger>

                  <DropdownMenuContent align='end' sideOffset={10}>
                    <UpdateCover journalId={journalId} />
                    <DropdownMenuItem>Delete Journal</DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
            </div>

            {journal.description && (
              <div className='italic text-white drop-shadow'>
                <Separator className='w-full h-0.5 bg-white' />
                {journal.description}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
