'use client';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import useCreateCollectionMutation from '@/hooks/query/useCreateCollectionMutation';
import useGetCollectionsQuery from '@/hooks/query/useGetCollections';
import { useState } from 'react';

export default function CollectionsTab() {
  const { mutate: createCollection } = useCreateCollectionMutation();
  const { data: collections } = useGetCollectionsQuery();

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

  return (
    <div className='max-w-7xl mx-auto'>
      <div className='mb-6 flex flex-col sm:flex-row gap-4 items-stretch sm:items-center justify-between'>
        <div className='w-full'>
          <Input type='text' />
        </div>

        <div className='flex gap-3'>
          <Button>Filter</Button>
          <Button
            onClick={() => {
              createCollection({
                body: {
                  name: 'New Collection',
                },
              });
            }}
          >
            Create Collection
          </Button>
        </div>
      </div>

      {collections &&
        collections.map((collection, index) => {
          return (
            <div key={index} className='py-4'>
              <div className='flex items-center'>
                <Button
                  variant='icon'
                  icon={selectedCollection === collection.id ? 'plus' : 'x'}
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

                    <Button variant='icon' icon='ellipsis-vertical' />
                  </div>

                  {selectedCollection === collection.id &&
                    (collection.journals.length === 0 ? (
                      <div>no journals</div>
                    ) : (
                      <div>
                        {collection.journals.map((journal, index) => (
                          <div key={index}>{journal.id}</div>
                        ))}
                      </div>
                    ))}
                </div>
              </div>
            </div>
          );
        })}
    </div>
  );
}
