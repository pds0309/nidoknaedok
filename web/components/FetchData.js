export async function FetchData(url, method, type, data, ...els) {
    if (method === 'GET') {
        const result = await fetch(url,
            {
                method: method,
            }
        );
        const json = await result.json();
        return json;
    }
    if (method === 'POST') {
        const result = await fetch(url,
            {
                method: method,
                headers: {
                    "Content-Type": type
                },
                body: data
            }
        );
        const json = await result.json();
        return json;
    }
}