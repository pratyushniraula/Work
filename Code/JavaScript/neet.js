function mostOccured(s)
{
    let histo = {};

    for(let i = 0; i < s.length; i++)
    {
       let char = s[i];
       if(!histo[char])
       {
         histo[char] = 0;
       }
       histo[char]++;
    }

    let maxChar = null;
    let maxCount = 0;
    for(let key in histo)
    {
        if(histo[key] > maxCount) // This condition is corrected to '>'
        {
            maxCount = histo[key];
            maxChar = key;
        }
    }
    console.log(maxChar, maxCount); // For logging the most occurred character and its count
    return maxChar; // Optionally return the most occurred character
}

console.log(mostOccured("helloworld"));
