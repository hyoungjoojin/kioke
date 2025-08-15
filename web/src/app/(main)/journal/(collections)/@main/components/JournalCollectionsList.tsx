'use client';

import Sidebar from './Sidebar';
import { useJournalCollectionsQuery } from '@/query/journal/getJournalCollections';
import { JournalCollection } from '@/types/journal';
import { AnimatePresence, motion } from 'motion/react';
import { useTranslations } from 'next-intl';
import { useState } from 'react';

interface SidebarState {
  open: boolean;
  selectedCollection: JournalCollection | null;
}

export default function JournalCollectionsList() {
  const t = useTranslations();

  const { data: collections } = useJournalCollectionsQuery();

  const [sidebar, setSidebar] = useState<SidebarState>({
    open: false,
    selectedCollection: null,
  });

  if (!collections) {
    return null;
  }

  return (
    <div className='flex justify-between'>
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

      <AnimatePresence>
        {sidebar.open && sidebar.selectedCollection && (
          <motion.div
            initial={{ opacity: 0, scale: 1, x: 20 }}
            animate={{ opacity: 1, scale: 1, x: 0 }}
            exit={{ opacity: 0, scale: 1, x: 20 }}
            transition={{ type: 'spring', stiffness: 300, damping: 30 }}
          >
            <Sidebar
              collection={sidebar.selectedCollection}
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
  );
}
