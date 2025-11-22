'use client';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Routes } from '@/constant/routes';
import useCreateCollectionMutation from '@/hooks/query/useCreateCollectionMutation';
import useGetCollectionsQuery from '@/hooks/query/useGetCollections';
import { cn } from '@/lib/utils';
import Link from 'next/link';
import { useState } from 'react';

function Collections() {
  const { data: collections } = useGetCollectionsQuery();
  const { mutate: createCollection } = useCreateCollectionMutation();

  const [selectedCollection, setSelectedCollection] = useState<string | null>(
    null,
  );

  function toggleSelectedCollection(id: string) {
    if (selectedCollection === id) {
      setSelectedCollection(null);
    } else {
      setSelectedCollection(id);
    }
  }

  function createCollectionButtonClickHandler() {
    createCollection({
      body: {
        name: 'New Collection',
      },
    });
  }

  return (
    <div className='max-w-7xl mx-auto'>
      <div className='mb-6 flex flex-col sm:flex-row gap-4 items-stretch sm:items-center justify-between'>
        <div className='w-full'>
          <Input type='text' />
        </div>

        <div className='flex gap-3'>
          <Button variant='icon' icon='filter' />
          <Button variant='icon' icon='plus' />
        </div>
      </div>

      {collections &&
        collections.map((collection, index) => {
          return (
            <div key={index} className='py-4'>
              <div className='flex items-center'>
                <Button
                  variant='icon'
                  icon={
                    selectedCollection === collection.id
                      ? 'chevron-down'
                      : 'chevron-right'
                  }
                  onClick={() => {
                    toggleSelectedCollection(collection.id);
                  }}
                  className='self-start'
                />

                <div className='grow'>
                  <div className='border-b flex items-center justify-between'>
                    <div>
                      <span className='font-bold mr-2'>{collection.name}</span>
                      <span className='text-xs'>
                        {collection.journals.length}
                      </span>
                    </div>

                    <Button variant='icon' icon='plus' />
                  </div>

                  {selectedCollection === collection.id &&
                    (collection.journals.length === 0 ? (
                      <div>Add journal</div>
                    ) : (
                      <ScrollArea className='h-full overflow-hidden '>
                        <div className='flex'>
                          {collection.journals.map((journal, index) => (
                            <div
                              key={index}
                              className='shadow-sm m-4 w-56 h-72'
                            >
                              <Link
                                href={Routes.JOURNAL(journal.id)}
                                className='hover:underline'
                              >
                                {journal.title}
                              </Link>
                            </div>
                          ))}
                        </div>
                      </ScrollArea>
                    ))}
                </div>
              </div>
            </div>
          );
        })}

      <div
        className={cn(
          'opacity-0 w-full flex items-center justify-center',
          'hover:opacity-100 hover:cursor-pointer',
        )}
      >
        <Button
          variant='icon'
          icon='plus'
          onClick={createCollectionButtonClickHandler}
        />
        Add Collection
      </div>
    </div>
  );
}

export default Collections;
