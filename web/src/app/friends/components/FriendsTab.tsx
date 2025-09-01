'use client';

import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import { useSearchProfileQuery } from '@/query/profile/searchProfiles';
import { useDebounce } from '@uidotdev/usehooks';
import { useState } from 'react';

export default function FriendsTab() {
  const [query, setQuery] = useState('');
  const debouncedQuery = useDebounce(query, 500);
  const { data: profiles } = useSearchProfileQuery(debouncedQuery);

  return (
    <>
      <Input
        icon={IconName.SEARCH}
        value={query}
        onChange={(e) => {
          setQuery(e.target.value);
        }}
      />

      {profiles &&
        profiles.map((profile, index) => {
          return (
            <div key={index}>
              {profile.userId}
              {profile.name} {profile.email}
            </div>
          );
        })}
    </>
  );
}
