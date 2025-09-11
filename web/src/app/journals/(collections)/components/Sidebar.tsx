import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Routes } from '@/constant/routes';
import { useClickOutside } from '@/hooks/useClickOutside';
import { cn } from '@/lib/utils';
import { useCollectionQuery } from '@/query/collection';
import { useDeleteCollectionMutationQuery } from '@/query/collection/deleteCollection';
import Link from 'next/link';
import { useRef } from 'react';

interface SidebarProps {
  collectionId: string;
  onClose?: () => void;
}

export default function Sidebar({ collectionId, onClose }: SidebarProps) {
  const sidebarRef = useRef(null);

  const { data: collection } = useCollectionQuery({ id: collectionId });
  const { mutate: deleteCollection } =
    useDeleteCollectionMutationQuery(collectionId);

  useClickOutside(sidebarRef, () => {
    if (onClose) {
      onClose();
    }
  });

  return (
    <Card
      className={cn(
        'rounded-2xl',
        'h-96',
        'max-md:absolute max-md:left-0 max-md:w-full',
        'w-96 lg:w-[40rem] xl:w-[64rem]',
      )}
      ref={sidebarRef}
    >
      <CardContent>
        <h1 className='text-2xl mb-5'>{collection && collection.name}</h1>

        <Button
          onClick={() => {
            deleteCollection();
          }}
        >
          Delete Collection
        </Button>

        {collection &&
          collection.journals.map((journal, index) => {
            return (
              <div className='hover:underline' key={index}>
                <Link href={Routes.JOURNAL(journal.id)}>{journal.title}</Link>
              </div>
            );
          })}
      </CardContent>
    </Card>
  );
}
