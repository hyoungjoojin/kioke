'use client';

import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Spinner } from '@/components/ui/spinner';
import { JournalType } from '@/constant/journal';
import { Routes } from '@/constant/routes';
import {
  useCreateJournalMutation,
  useInfiniteJournalsQuery,
} from '@/query/journal';
import type { Journal } from '@/types/journal';
import { useIntersectionObserver } from '@uidotdev/usehooks';
import Link from 'next/link';
import { useEffect, useRef } from 'react';

export default function JournalsTab() {
  const scrollAreaRef = useRef<HTMLDivElement | null>(null);
  const [ref, entry] = useIntersectionObserver({
    threshold: 1,
    root: scrollAreaRef.current,
  });

  const { mutate: createJournal } = useCreateJournalMutation();
  const {
    data: journals,
    fetchNextPage,
    isFetchingNextPage,
  } = useInfiniteJournalsQuery();

  useEffect(() => {
    if (entry?.isIntersecting && !isFetchingNextPage) {
      fetchNextPage();
    }
  }, [entry, isFetchingNextPage, fetchNextPage]);

  return (
    <div className='h-full flex flex-col'>
      <div className='flex gap-2'>
        <div className='grow'>
          <Input type='search' />
        </div>

        <div className='flex gap-2'>
          <Button icon={IconName.SEARCH} onClick={() => {}} />

          <Button
            icon={IconName.PLUS}
            onClick={() => {
              createJournal({
                title: 'Untitled',
                type: JournalType.BASIC,
              });
            }}
          />
        </div>
      </div>

      <ScrollArea ref={scrollAreaRef} className='grow overflow-hidden'>
        {journals &&
          journals.pages
            .flatMap((page) => page.content)
            .map((journal, index) => (
              <JournalPreview key={index} data={journal} />
            ))}

        <div>
          {isFetchingNextPage && (
            <div>
              <Spinner />
            </div>
          )}
        </div>

        <div ref={ref}></div>
      </ScrollArea>
    </div>
  );
}

function JournalPreview({ data: journal }: { data: Journal }) {
  switch (journal.type) {
    case JournalType.BASIC:
      return (
        <Link href={Routes.JOURNAL(journal.id)}>
          <BasicJournalPreview journal={journal} />
        </Link>
      );

    case JournalType.SHORT:
    case JournalType.GALLERY:
    default:
      return null;
  }
}

function BasicJournalPreview({ journal }: { journal: Journal }) {
  return (
    <Card className='m-2'>
      {journal.id} {journal.title}
    </Card>
  );
}
