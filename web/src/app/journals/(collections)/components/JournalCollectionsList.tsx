'use client';

import Sidebar from './Sidebar';
import { Button } from '@/components/ui/button';
import { IconName } from '@/components/ui/icon';
import {
  useCollectionsQuery,
  useCreateCollectionMutationQuery,
} from '@/query/collection';
import type { Collection } from '@/types/collection';
import { AnimatePresence, motion } from 'motion/react';
import { useTranslations } from 'next-intl';
import { useState } from 'react';

interface SidebarState {
  open: boolean;
  selectedCollection: Collection | null;
}

export default function JournalCollectionsList() {
  const t = useTranslations();

  const { data: collections } = useCollectionsQuery();
  const {
    mutate: createJournalCollection,
    isPending: isCreateJournalCollectionPending,
  } = useCreateCollectionMutationQuery();

  const [sidebar, setSidebar] = useState<SidebarState>({
    open: false,
    selectedCollection: null,
  });

  if (!collections) {
    return null;
  }

  const onCreateJournalCollectionButtonClick = () => {
    createJournalCollection({
      name: t('default-values.initial-journal-collection'),
    });
  };

  return (
    <div className='flex flex-col'>
      <Button
        icon={IconName.PLUS}
        className='self-end mb-10'
        pending={isCreateJournalCollectionPending}
        onClick={onCreateJournalCollectionButtonClick}
      >
        Add
      </Button>
      <div className='flex justify-between'>
        <div>
          {collections.map((collection, index) => {
            return (
              <div
                onClick={() => {
                  setSidebar(() => ({
                    open: true,
                    selectedCollection: collection,
                  }));
                }}
                className='hover:underline hover:cursor-pointer mb-5'
                key={index}
              >
                {collection.name}
              </div>
            );
          })}
        </div>

        <AnimatePresence>
          {sidebar.open && sidebar.selectedCollection && (
            <motion.div
              initial={{ opacity: 0, scale: 1, x: 20 }}
              animate={{ opacity: 1, scale: 1, x: 0 }}
              exit={{ opacity: 0, scale: 1, x: 20 }}
              transition={{ type: 'spring', stiffness: 300, damping: 30 }}
            >
              <Sidebar
                collectionId={sidebar.selectedCollection.id}
                onClose={() => {
                  setSidebar((sidebar) => ({
                    ...sidebar,
                    open: false,
                  }));
                }}
              />
            </motion.div>
          )}
        </AnimatePresence>
      </div>
    </div>
  );
}
