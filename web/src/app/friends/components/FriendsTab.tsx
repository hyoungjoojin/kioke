'use client';

import { searchProfiles } from '@/app/api/profile';
import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import { useState } from 'react';

export default function FriendsTab() {
  const [query, setQuery] = useState('');

  return (
    <>
      <Input
        icon={IconName.SEARCH}
        value={query}
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            searchProfiles({
              query,
            });
          }
        }}
        onChange={(e) => {
          setQuery(e.target.value);
        }}
      />
    </>
  );
}
